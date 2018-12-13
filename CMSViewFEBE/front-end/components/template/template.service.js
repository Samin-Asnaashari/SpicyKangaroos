angular.module('appComponent').service('templateService', function block(sectionService, blockService, elementService, styleService) {

    var self = this;

    self.buildTemplate1 = function (pageId, sequence, sectionToDuplicate) {
        //--------------------------------------------------
        var row = {};
        row.id = null;
        row.sectionType = "Row";
        row.identifier = "template-1";
        row.sequence = sequence;
        row.page = {};
        row.page.id = pageId;
        row.style = null;
        //--------------------------------------------------
        var block = {};
        block.id = null;
        block.identifier = "block-text";
        block.parent = row; // Set after row is created
        block.style = null;
        //--------------------------------------------------
        var element = {};
        element.id = null;
        element.elementType = "Text";
        element.identifier = "element";
        element.content = "";
        element.parent = block; // Set after block is created
        //--------------------------------------------------
        return sectionService.createSection(row)
            .then(function (sectionResponse) {
                row = sectionResponse.data;
                block.parent = row;
                return blockService.createBlock(block);
            })
            .then(function (blockResponse) {
                block = blockResponse.data;
                element.parent = block;
                if (sectionToDuplicate != null) {
                    //duplicate
                    element.content = sectionToDuplicate.blocks[0].elements[0].content;
                }
                return elementService.createElement(element);
            })
            .then(function (elementResponse) {
                element = elementResponse.data;

                if (sectionToDuplicate == null) {
                    //create new
                    //Pre-defined style (row)
                    row.style.backgroundImage = "asset/img/default-image.png";
                    row.style.imageWidth = 1236;
                    row.style.imageHeight = 617;
                } else {
                    //duplicate
                    row.style.backgroundImage = sectionToDuplicate.style.backgroundImage;
                    row.style.imageWidth = sectionToDuplicate.style.imageWidth;
                    row.style.imageHeight = sectionToDuplicate.style.imageHeight;
                }
                return styleService.updateStyle(row.style);
            })
            .then(function (styleResponse) {
                if (sectionToDuplicate == null) {
                    //create new
                    //Pre-defined style (block)
                    block.style.textColor = "rgb(255, 255, 255)";
                    block.style.backgroundColor = "rgba(77, 77, 77, 0.60)";
                } else {
                    //duplicate
                    block.style.textColor = sectionToDuplicate.blocks[0].style.textColor;
                    block.style.backgroundColor = sectionToDuplicate.blocks[0].style.backgroundColor;
                }
                return styleService.updateStyle(block.style);
            })
            .then(function (styleResponse) {
                return sectionService.findOneSection(row.id);
            })
            .then(function (sectionResponse) {
                row = sectionResponse.data;
                return row;
            });
    };

    self.buildTemplate2 = function (pageId, sequence, sectionToDuplicate) {
        //--------------------------------------------------
        var row = {};
        row.id = null;
        row.sectionType = "Row";
        row.identifier = "template-2";
        row.sequence = sequence;
        row.page = {};
        row.page.id = pageId;
        row.style = null;
        //--------------------------------------------------
        var block = {};
        block.id = null;
        block.identifier = "block-text";
        block.parent = row; // Set after row is created
        block.style = null;
        //--------------------------------------------------
        var element = {};
        element.id = null;
        element.elementType = "Text";
        element.identifier = "element";
        element.content = "";
        element.parent = block; // Set after block is created
        //--------------------------------------------------

        return sectionService.createSection(row)
            .then(function (sectionResponse) {
                row = sectionResponse.data;
                block.parent = row;
                return blockService.createBlock(block);
            })
            .then(function (blockResponse) {
                block = blockResponse.data;
                element.parent = block;
                if (sectionToDuplicate != null) {
                    //duplicate
                    element.content = sectionToDuplicate.blocks[0].elements[0].content;
                }
                return elementService.createElement(element);
            })
            .then(function (elementResponse) {
                element = elementResponse.data;
                if (sectionToDuplicate == null) {
                    //Pre-defined style (row)
                    row.style.backgroundColor = "rgb(0, 0, 0)";
                    row.style.textColor = "rgb(255, 255, 255)";
                    row.style.paddingTop = 50;
                    row.style.paddingBottom = 50;
                } else {
                    //duplicate
                    row.style.backgroundColor = sectionToDuplicate.style.backgroundColor;
                    row.style.textColor = sectionToDuplicate.style.textColor;
                    row.style.paddingTop = sectionToDuplicate.style.paddingTop;
                    row.style.paddingBottom = sectionToDuplicate.style.paddingBottom;
                }

                return styleService.updateStyle(row.style);
            })
            .then(function (styleResponse) {
                return sectionService.findOneSection(row.id);
            })
            .then(function (sectionResponse) {
                row = sectionResponse.data;
                return row;
            });
    };

    self.buildTemplate3 = function (pageId, sequence, sectionToDuplicate) {
        //--------------------------------------------------
        var row = {};
        row.id = null;
        row.sectionType = "Row";
        row.identifier = "template-3";
        row.sequence = sequence;
        row.page = {};
        row.page.id = pageId;
        row.style = null;
        //--------------------------------------------------
        var block = {};
        block.id = null;
        block.identifier = "block-text-left";
        block.parent = row; // Set after row is created
        block.style = null;
        //--------------------------------------------------
        var element = {};
        element.id = null;
        element.elementType = "Text";
        element.identifier = "element";
        element.content = "";
        element.parent = block; // Set after block is created
        //--------------------------------------------------

        return sectionService.createSection(row)
            .then(function (sectionResponse) {
                row = sectionResponse.data;
                block.parent = row;
                return blockService.createBlock(block); // block-text-left
            })
            .then(function (blockResponse) {
                block = blockResponse.data;
                element.parent = block;
                if (sectionToDuplicate != null) {
                    //duplicate
                    element.content = sectionToDuplicate.blocks[0].elements[0].content;
                }
                return elementService.createElement(element);
            })
            .then(function (elementResponse) {
                // Element created successfully
                block = {};
                block.identifier = "block-text-right";
                block.parent = row;
                element.id = null;
                return blockService.createBlock(block); // block-text-right
            })
            .then(function (blockResponse) {
                block = blockResponse.data;
                element.parent = block;
                if (sectionToDuplicate != null) {
                    //duplicate
                    element.content = sectionToDuplicate.blocks[1].elements[0].content;
                }
                return elementService.createElement(element);
            })
            .then(function (elementResponse) {
                element = elementResponse.data;

                if (sectionToDuplicate == null) {
                    //Pre-defined style (row)
                    row.style.backgroundColor = "rgb(49, 49, 49)";
                    row.style.textColor = "rgb(255, 255, 255)";
                    row.style.paddingTop = 50;
                    row.style.paddingBottom = 50;
                }else{
                    //duplicate
                    row.style.backgroundColor = sectionToDuplicate.style.backgroundColor;
                    row.style.textColor = sectionToDuplicate.style.textColor;
                    row.style.paddingTop = sectionToDuplicate.style.paddingTop;
                    row.style.paddingBottom = sectionToDuplicate.style.paddingBottom;
                }
                return styleService.updateStyle(row.style);
            })
            .then(function (styleResponse) {
                return sectionService.findOneSection(row.id);
            })
            .then(function (sectionResponse) {
                row = sectionResponse.data;
                return row;
            });
    };
});